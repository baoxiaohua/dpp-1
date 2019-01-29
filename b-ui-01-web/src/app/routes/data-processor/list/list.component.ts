import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { _HttpClient, ModalHelper, TitleService } from '@delon/theme';
import { STColumn, STComponent, ReuseTabService, STChange, STColumnTag } from '@delon/abc';
import { SFSchema, SFComponent, SFUISchema } from '@delon/form';
import { BasePageComponent } from '@shared/base-page/base-page.component';
import { Router, ActivatedRoute } from '@angular/router';
import { EventBusService } from 'app/service/event-bus.service';
import { TranslateService } from '@ngx-translate/core';
import { DataProcessorService } from 'app/service/core/data-processor.service';
import { IDataProcessor } from 'app/model/core/data-processor.model';
import { DataProcessorCreateComponent } from '../create/create.component';
import { LayoutService } from 'app/service/layout.service';
import { NzMessageService } from 'ng-zorro-antd';
import * as moment from 'moment';

const STATE_TAG: STColumnTag = {
  'DRAFT': { text: '起草', color: 'blue' },
  'ENABLED': { text: '启用', color: 'green' },
  'DISABLED': { text: '停用', color: 'orange' },
  'ERROR': { text: '错误', color: 'red' },
};

@Component({
  selector: 'app-data-processor-list',
  templateUrl: './list.component.html',
})
export class DataProcessorListComponent extends BasePageComponent implements OnInit {
  stData = {
    list: null,
    total: null,
    pageSize: 50,
    defaultSort: ['updateTs,desc'],
    sort: ['updateTs,desc']
  };

  searchSchema: SFSchema = {
    properties: {
      'nameSpace.contains': {
        type: 'string',
        title: this.translate('nameSpace'),
      },
      'name.contains': {
        type: 'string',
        title: this.translate('name'),
      },
      'identifier.contains': {
        type: 'string',
        title: this.translate('identifier'),
      },
    },
  };

  @ViewChild('st') st: STComponent;
  @ViewChild('sf') sf: SFComponent;
  @ViewChild('sfDiv') sfDiv: ElementRef;

  stHeightOffset = 3000;

  columns: STColumn[] = [
    { title: 'ID', index: 'id', width: '100px', sort: true, fixed: 'left' },
    { i18n: 'identifier', title: '', index: 'identifier', width: '400px', sort: true, fixed: 'left'  },
    { i18n: 'nameSpace', title: '', index: 'nameSpace', width: '200px', sort: true },
    { i18n: 'name', title: '', index: 'name', sort: true },
    { i18n: 'state', title: '', index: 'state', sort: true, width: '100px', type: 'tag', tag: STATE_TAG },
    {
      i18n: 'updateTs',
      title: '',
      type: 'date',
      index: 'updateTs',
      dateFormat: 'YYYY-MM-DD HH:mm:ss',
      width: '155px',
      sort: true,
    },
    {
      i18n: 'operation',
      title: '',
      width: '160px',
      fixed: 'right',
      buttons: [
        {
          i18n: 'delete',
          type: 'del',
          click: item => {
            this.loading = true;
            this.dataProcessorService.delete(item.id).subscribe(resp => {
              this.msgSrv.success('删除成功');
              this.loadTable();
            });
          }
        },
        {
          i18n: 'edit',
          click: item => {
            this.navigate(`data-processor/edit/${item.id}`, null);
          }
        },
      ],
    },
  ];

  constructor(
    router: Router,
    activatedRoute: ActivatedRoute,
    reuseTabService: ReuseTabService,
    eventBusService: EventBusService,
    translateService: TranslateService,
    titleService: TitleService,
    private dataProcessorService: DataProcessorService,
    private modal: ModalHelper,
    private layoutService: LayoutService,
    private msgSrv: NzMessageService,
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

  ngOnInit() {
    super.ngOnInit();
  }

  initVariable(params, queryParams): void {}

  loadPage(): void {
    this.setTitle('data-processor', null);
    this.loadTable();
  }

  eventHandler(event: string): void {
    if (event === 'data_processor_list:refresh') this.loadTable();
  }

  loadTable() {
    const tmpParams = Object.assign({page: this.st.pi - 1, size: this.stData.pageSize, sort: this.stData.sort}, this.sf.value);

    this.loading = true;
    this.dataProcessorService.query(tmpParams).subscribe(resp => {
      this.stData.total = parseInt(resp.headers.get('X-Total-Count'), 10);
      this.stData.list = resp.body;
      this.loading = false;
    });
  }

  change(e: STChange) {
    if (e.type === 'pi') {

    } else if (e.type === 'sort') {
      this.stData.sort = this.convertSortData(e.sort);
    } else return;

    this.loadTable();
  }

  search() {
    this.st.pi = 1;
    this.loadTable();
  }

  reset() {
    this.sf.reset();
    this.stData.sort = this.stData.defaultSort;
    this.st.reset();
  }

  add() {
    this.modal.createStatic(DataProcessorCreateComponent, { i: { id: 0 } }).subscribe((result) => {
      this.navigate(`data-processor/edit/${result.id}`, null);
      this.loadTable();
    });
  }

  onWindowResize(event) {
    this.stHeightOffset = this.layoutService.contentHeightOffset + this.sfDiv.nativeElement.offsetHeight + 120;
  }
}
