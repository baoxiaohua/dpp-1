import { Component, OnInit, ViewChild } from '@angular/core';
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
import { DataSubProcessor, IDataSubProcessor } from 'app/model/core/data-sub-processor.model';
import { Editor } from 'codemirror';
import { switchArrayElements } from 'app/util/array-util';
import { DataSubProcessorCustomService } from 'app/service/core/custom/data-sub-processor.custom.service';

@Component({
  selector: 'app-data-processor-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.less'],
})
export class DataProcessorEditComponent extends BasePageComponent implements OnInit {
  dataProcessorId;

  // #region CodeMirrorEditor
  cmEditor: Editor = null;

  cmOptions: any = {
    lineNumbers: true,
    // mode: { name: 'text/x-groovy' },
    mode: { name: 'text/x-pgsql' },
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
    { title: '', index: 'sequence', width: '50px' },
    { title: '', index: 'name' },
    {
      title: '',
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
      name: { type: 'string', title: '名称', maxLength: 15 },
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
      outputAsTable: { type: 'boolean', title: '输出表' },
      outputAsObject: { type: 'boolean', title: '输出对象' },
      outputAsResult: { type: 'boolean', title: '输出结果' },
    },
    required: ['name', 'dataProcessorType'],
  };

  dataSubProcessorUI: SFUISchema = {
    '*': { spanLabelFixed: 90 },
    $dataProcessorType: {
      widget: 'select',
      change: value => {
        this.cmEditor.setOption('mode', { name: 'text/x-groovy' });
      }
    },
  };

  parameterColumn: STColumn[] = [
    { title: '参数', index: 'name', width: '80px' },
    { title: '可选', index: 'optional', width: '50px', type: 'radio' },
    { title: '类型', index: 'type', width: '80px' },
    { title: '测试值', index: 'value' },
  ];
  // #endregion

  constructor(
    router: Router,
    activatedRoute: ActivatedRoute,
    reuseTabService: ReuseTabService,
    eventBusService: EventBusService,
    translateService: TranslateService,
    private dataProcessorService: DataProcessorService,
    private dataSubProcessorService: DataSubProcessorService,
    private msgSrv: NzMessageService,
    private layoutService: LayoutService,
    private codemirrorService: CodemirrorService,
    private dataSubProcessorCustomService: DataSubProcessorCustomService,
  ) {
    super(
      router,
      activatedRoute,
      reuseTabService,
      eventBusService,
      translateService,
    );
  }

  ngOnInit(): void {
    super.ngOnInit();

    this.codemirrorService.instance$.subscribe((editor) => {
      this.cmEditor = editor;
      editor.setSize('100%', `calc(100vh - ${this.layoutService.contentHeightOffset}px - 37px)`);
    });
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
  }

  loadDataSubProcessorList() {
    this.dataSubProcessorListLoading = true;
    this.dataSubProcessorService.query({'dataProcessorId.equals': this.dataProcessorId, sort: ['sequence,asc']}).subscribe(resp => {
      this.dataSubProcessorListLoading = false;
      this.dataSubProcessorList = resp.body;
    });
  }

  eventHandler(event: string): void {

  }

  submitDataProcessor(formData) {
    this.dataProcessorLoading = true;
    this.dataProcessorService.update(formData).subscribe(resp => {
      this.dataProcessorLoading = false;
      this.dataProcessor = resp.body;
      this.emitEvent('data_processor_list:refresh');
    });
  }

  addSubProcessor() {
    this.dataSubProcessor = new DataSubProcessor();
  }

  submitSubDataProcessor() {
    const formData = this.dataSubProcessorSf.value;
    this.dataSubProcessorLoading = true;
    formData['code'] = this.dataSubProcessor.code;

    if (!formData['id']) {
      formData['sequence'] = this.dataSubProcessorList.length + 1;
      formData['dataProcessorId'] = this.dataProcessor.id;
      this.dataSubProcessorService.create(formData).subscribe(resp => {
        this.dataSubProcessorLoading = false;
        this.dataSubProcessor = resp.body;

        this.loadDataSubProcessorList();
      });
    } else {
      const tmp = this.dataSubProcessorList.find(item => item.id === formData['id']);
      if (!!tmp) formData['sequence'] = tmp.sequence;

      this.dataSubProcessorService.update(formData).subscribe(resp => {
        this.dataSubProcessorLoading = false;
        this.dataSubProcessor = resp.body;

        this.loadDataSubProcessorList();
      });
    }
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

  onCodeChange() {
    console.log(this.dataSubProcessor.code);
  }
}
