import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { SFSchema, SFUISchema, SFComponent } from '@delon/form';
import { BasePageComponent } from '@shared/base-page/base-page.component';
import { Router, ActivatedRoute } from '@angular/router';
import { ReuseTabService, STColumn } from '@delon/abc';
import { EventBusService } from 'app/service/event-bus.service';
import { TranslateService } from '@ngx-translate/core';

import 'codemirror/mode/sql/sql';
import 'codemirror/addon/hint/show-hint.js';
import 'codemirror/addon/hint/sql-hint.js';

import 'codemirror/mode/groovy/groovy';


import { CodemirrorService } from '@nomadreservations/ngx-codemirror';
import { LayoutService } from 'app/service/layout.service';
import { DataProcessorService } from 'app/service/core/data-processor.service';
import { DataSubProcessorService } from 'app/service/core/data-sub-processor.service';
import { DataProcessor, IDataProcessor } from 'app/model/core/data-processor.model';
import { DataSubProcessor, IDataSubProcessor, DataProcessorType } from 'app/model/core/data-sub-processor.model';
import { Editor } from 'codemirror';
import { switchArrayElements } from 'app/util/array-util';
import { DataSubProcessorCustomService } from 'app/service/core/custom/data-sub-processor.custom.service';
import { TitleService } from '@delon/theme';
import { BehaviorSubject, Observable } from 'rxjs';
import { debounceTime, switchMap } from 'rxjs/operators';
import * as moment from 'moment';
import { DataProcessorParameterCustomService } from 'app/service/core/custom/data-processor-parameter.custom.service';
import { DataProcessorCustomService } from 'app/service/core/custom/data-processor.custom.service';

@Component({
  selector: 'app-data-processor-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.less'],
})
export class DataProcessorEditComponent extends BasePageComponent implements OnInit {

  dataProcessorId;

  dataProcessorSfDirty = false;
  dataSubProcessorSfDirty = false;
  dataSubProcessorCodeDirty = false;

  // #region Right side tab card
  code;
  dataSubProcessorResult = {};
  selectedTabIndex = 0;
  tabCardLoading = false;
  codeChange$ = new BehaviorSubject('');
  // #endregion

  // #region CodeMirrorEditor
  cmEditor: Editor = null;

  cmOptions: any = {
    lineNumbers: true,
    // mode: { name: 'text/x-groovy' },
    mode: { name: 'text/x-sql' },
    theme: 'dracula'
  };
  // #endregion

  // #region DataProcessor
  dataProcessorLoading = false;
  dataProcessor: IDataProcessor = new DataProcessor();

  @ViewChild('dataProcessorSf') dataProcessorSf: SFComponent;

  dataProcessorSchema: SFSchema = {
    properties: {
      id: { type: 'number', title: 'ID'},
      nameSpace: { type: 'string', title: '命名空间' },
      name: { type: 'string', title: '名称', maxLength: 15 },
      identifier: { type: 'string', title: '标识符' },
      restApi: { type: 'boolean', title: 'Rest接口' },
    },
    required: ['nameSpace', 'name', 'identifier'],
    ui: {
      grid: {
        span: 24
      }
    }
  };

  dataProcessorUI: SFUISchema = {
    '*': { spanLabelFixed: 90 },
    $id: { widget: 'text' },
  };
  // #endregion

  // #region DataSubProcessor List
  dataSubProcessorListLoading = false;
  dataSubProcessorList = [];
  dataSubProcessorColumns: STColumn[] = [
    { title: '序号', index: 'sequence', width: '50px' },
    { title: '名称', index: 'name' },
    {
      title: '操作',
      width: '140px',
      fixed: 'right',
      buttons: [
        {
          title: '',
          icon: 'up',
          click: item => {
            const index = this.dataSubProcessorList.findIndex(rd => rd.id === item.id);
            this.dataSubProcessorList = switchArrayElements(this.dataSubProcessorList, index, index - 1);
            this.saveSubDataProcessorSequence();
          }
        },
        {
          title: '',
          icon: 'down',
          click: item => {
            const index = this.dataSubProcessorList.findIndex(rd => rd.id === item.id);
            this.dataSubProcessorList = switchArrayElements(this.dataSubProcessorList, index, index + 1);
            this.saveSubDataProcessorSequence();
          }
        },
        {
          title: '',
          icon: 'delete',
          type: 'del',
          click: item => {
            this.dataSubProcessorListLoading = true;
            this.dataSubProcessorService.delete(item.id).subscribe(resp => {
              if (this.dataSubProcessor.id === item.id) this.dataSubProcessor = new DataSubProcessor();
              this.loadDataSubProcessorList();
            });
          }
        },
        {
          title: '',
          icon: 'edit',
          click: item => {
            this.dataSubProcessor = item as IDataProcessor;
            this.code = this.dataSubProcessor.code;
            this.changeCodeMode(this.dataSubProcessor.dataProcessorType);
            this.selectedTabIndex = 0;
          }
        },
      ],
    },
  ];
  // #endregion

  // #region DataSubProcessor form
  dataSubProcessorLoading = false;
  dataSubProcessor: IDataSubProcessor = new DataSubProcessor();

  @ViewChild('dataSubProcessorSf') dataSubProcessorSf: SFComponent;

  dataSubProcessorSchema: SFSchema = {
    properties: {
      name: { type: 'string', title: '名称', maxLength: 50 },
      dataProcessorType: {
        type: 'string',
        title: '类型',
        enum: [
          { label: 'Sql-中间结果', value: 'SQL_INTERIM' },
          { label: 'Sql-服务DB', value: 'SQL_DB' },
          { label: 'Sql-Kylin', value: 'SQL_KYLIN' },
          { label: '脚本-Groovy', value: 'CODE_GROOVY' }
        ]
      },
      outputAsTable: { type: 'boolean', title: '输出表', ui: {grid: { span: 9 }}},
      outputAsObject: { type: 'boolean', title: '输出对象', ui: {grid: { span: 9 }} },
      outputAsResult: { type: 'boolean', title: '输出结果' },
    },
    required: ['name', 'dataProcessorType'],
    ui: {
      grid: {
        span: 24
      }
    }
  };

  dataSubProcessorUI: SFUISchema = {
    '*': { spanLabelFixed: 90 },

    $dataProcessorType: {
      widget: 'select',
      change: value => {
        this.changeCodeMode(value);
      }
    },
  };
  // #endregion

  // #region parameterList
  dataProcessorParameter = {};
  parameterListLoading = false;

  parameterList = [];

  parameterColumn: STColumn[] = [
    { title: '参数', index: 'name', width: '80px' },
    { title: '测试值', index: 'value' },
  ];
  // #endregion

  constructor(
    router: Router,
    activatedRoute: ActivatedRoute,
    reuseTabService: ReuseTabService,
    eventBusService: EventBusService,
    translateService: TranslateService,
    titleService: TitleService,
    private dataProcessorService: DataProcessorService,
    private dataSubProcessorService: DataSubProcessorService,
    private msgSrv: NzMessageService,
    private layoutService: LayoutService,
    private codemirrorService: CodemirrorService,
    private dataSubProcessorCustomService: DataSubProcessorCustomService,
    private changeRef: ChangeDetectorRef,
    private dataProcessorParameterCustomService: DataProcessorParameterCustomService,
    private dataProcessorCustomService: DataProcessorCustomService,
  ) {
    super(
      router,
      activatedRoute,
      reuseTabService,
      eventBusService,
      translateService,
      titleService,
    );
  }

  ngOnInit(): void {
    super.ngOnInit();

    this.codemirrorService.instance$.subscribe((editor) => {
      this.cmEditor = editor;
      editor.setSize('100%', `calc(100vh - ${this.layoutService.contentHeightOffset}px - 37px)`);
    });

    // const code$: Observable<string> = this.codeChange$.asObservable().pipe(debounceTime(500));
    // code$.subscribe(this.codeChangeHandler);
  }

  initVariable(params, queryParams): void {
    this.dataProcessorId = params['id'];
  }

  loadPage(): void {
    this.setTitle('data-processor', this.dataProcessorId);
    this.loadDataProcessor();
    this.loadDataSubProcessorList();
  }

  loadDataProcessor() {
    this.dataProcessorLoading = true;
    this.dataProcessorService.find(this.dataProcessorId).subscribe(resp => {
      this.dataProcessorLoading = false;
      this.dataProcessor = resp.body;
    });

    this.parameterListLoading = true;
    this.dataProcessorParameterCustomService.findByDataProcessorId(this.dataProcessorId).subscribe(resp => {
      this.parameterListLoading = false;

      if (!resp.body) {
        this.dataProcessorParameter = {};
        this.parameterList = [];
        return;
      }
      this.dataProcessorParameter = resp.body;
      this.parameterList = JSON.parse(this.dataProcessorParameter['json']);
    });
  }

  loadDataSubProcessorList() {
    this.dataSubProcessorListLoading = true;
    this.dataSubProcessorService.query({'dataProcessorId.equals': this.dataProcessorId, sort: ['sequence,asc']}).subscribe(resp => {
      this.dataSubProcessorList = resp.body;

      if (this.dataSubProcessorList.length > 0 && !this.dataSubProcessor.id) {
        this.dataSubProcessor = this.dataSubProcessorList[0];
        this.code = this.dataSubProcessor.code;
        this.changeCodeMode(this.dataSubProcessor.dataProcessorType);
      }

      this.dataSubProcessorListLoading = false;
    });
  }

  eventHandler(event: string): void {

  }

  submitDataProcessor() {
    const formData = this.dataProcessorSf.value;
    this.dataProcessorLoading = true;
    formData['updateTs'] = moment(Date.now());
    this.dataProcessorService.update(formData).subscribe(resp => {
      this.dataProcessorLoading = false;
      this.dataProcessor = resp.body;
      this.emitEvent('data_processor_list:refresh');
    });
  }

  executeDataProcessor() {
    this.execute(-1);
  }

  addSubProcessor() {
    this.dataSubProcessor = new DataSubProcessor();
    this.code = '';
    this.changeCodeMode(this.dataSubProcessor.dataProcessorType);
  }

  dataSubProcessorSfChange(event) {
    // console.log('formchange', this.dataSubProcessorListLoading);
  }

  submitSubDataProcessor(callback?) {
    const formData = this.dataSubProcessorSf.value;
    this.dataSubProcessorLoading = true;
    this.tabCardLoading = true;
    formData['code'] = this.code;
    formData['updateTs'] = moment(Date.now());

    if (!formData['id']) {
      formData['sequence'] = this.dataSubProcessorList.length + 1;
      formData['dataProcessorId'] = this.dataProcessor.id;
      formData['createTs'] = moment(Date.now());
      this.dataSubProcessorService.create(formData).subscribe(resp => {
        this.dataSubProcessorLoading = false;
        this.tabCardLoading = false;
        this.dataSubProcessor = resp.body;

        this.loadDataSubProcessorList();

        if (callback) callback();
      });
    } else {
      const tmp = this.dataSubProcessorList.find(item => item.id === formData['id']);
      if (!!tmp) formData['sequence'] = tmp.sequence;

      this.dataSubProcessorService.update(formData).subscribe(resp => {
        this.dataSubProcessorLoading = false;
        this.tabCardLoading = false;
        this.dataSubProcessor = resp.body;

        this.loadDataSubProcessorList();

        if (callback) callback();
      });
    }
  }

  executeSubDataProcessor() {
    this.execute(this.dataSubProcessor.id);
  }

  execute(dataSubProcessorId) {
    let criteriaJson = '{';
    this.parameterList.forEach(item => {
      criteriaJson += '"' + item['name'] + '": ' + item['value'] + ',';
    });
    criteriaJson += '"debug": true }';

    const criteria = JSON.parse(criteriaJson);

    this.dataSubProcessorLoading = true;
    this.tabCardLoading = true;
    this.submitSubDataProcessor(() => {
      this.dataProcessorCustomService.debug(this.dataProcessor.identifier, dataSubProcessorId, criteria).subscribe(resp => {
        this.dataSubProcessorResult = resp.body;
        this.selectedTabIndex = 1;

        this.dataSubProcessorLoading = false;
        this.tabCardLoading = false;
      });
    });

    this.parameterListLoading = true;
    this.dataProcessorParameter['json'] = JSON.stringify(this.parameterList);
    this.dataProcessorParameter['dataProcessorId'] = this.dataProcessorId;
    this.dataProcessorParameterCustomService.save(this.dataProcessorParameter).subscribe(resp => {
      this.dataProcessorParameter = resp.body;
      this.parameterListLoading = false;
    });
  }

  resetSubDataProcessor() {
    this.dataSubProcessorSf.reset();
  }

  saveSubDataProcessorSequence() {
    const idList: number[] = this.dataSubProcessorList.map(item => item.id);

    this.dataSubProcessorListLoading = true;
    this.dataSubProcessorCustomService.reorder(this.dataProcessorId, idList).subscribe(resp => {
      this.dataSubProcessorList = resp.body;
      this.dataSubProcessorListLoading = false;
    });
  }

  onCodeChange(event) {
    if (!!event.type) return;

    this.dataSubProcessorCodeDirty = true;

    // if (!this.parameterStLoading) {
    //   this.parameterStLoading = true;
    //   this.changeRef.detectChanges();
    // }

    // this.codeChange$.next(event);

    // this.parameterStLoading = true;
    // this.parameterList = this.parseSql(event);
    // this.parameterStLoading = false;
  }

  // codeChangeHandler = (newCode) => {

  //   this.parameterStLoading = false;
  //   this.changeRef.detectChanges();

  //   if (newCode === null) return;

  //   this.parameterList = this.parseSql(newCode);

  //   setTimeout(() => {
  //     this.codeChange$.next(null);
  //   }, 500);
  // }


  parseSql(sql) {
    console.log(sql);
    const pattern = /:[a-z|0-9]+/gi;
    const results = this.code.match(pattern);

    const resParamList = [];

    results.forEach(element => {
      const param = element.replace(':', '');

      if (resParamList.find(item => item.name === param)) return;

      const existingParam = this.parameterList.find(item => item.name === param);

      if (existingParam) resParamList.push(existingParam);
      else resParamList.push({name: param, type: 'text', value: ''});

    });

    return resParamList;
  }

  addParameter(type) {
    const arr = Object.assign([], this.parameterList);

    if (type === 'paging') {
      arr.push({name: 'pageNum', value: '1'});
      arr.push({name: 'pageSize', value: '50'});
    } else if (type === 'sorting') arr.push({name: 'sort', value: '[]'});
    else if (type === 'empty') arr.push({name: '', value: ''});

    this.parameterList = arr;
  }

  deleteParameter(index) {
    const arr = Object.assign([], this.parameterList);
    arr.splice(index, 1);
    this.parameterList = arr;
  }

  changeCodeMode(value) {
    let name = '';

    if (value === DataProcessorType.CODE_GROOVY) name = 'text/x-groovy';
    else if (value === DataProcessorType.SQL_KYLIN) name = 'text/x-sql';
    else if (value === DataProcessorType.SQL_DB) name = 'text/x-pgsql';
    else if (value === DataProcessorType.SQL_INTERIM) name = 'text/x-sqlite';

    this.cmEditor.setOption('mode', { name: name });
  }

  enableDataProcessor(enabled) {
    this.dataProcessorLoading = true;

    this.dataProcessorCustomService.enable(this.dataProcessorId, enabled).subscribe(resp => {
      this.dataProcessorLoading = false;
      this.dataProcessor = resp.body;
      this.emitEvent('data_processor_list:refresh');
    });
  }
}
