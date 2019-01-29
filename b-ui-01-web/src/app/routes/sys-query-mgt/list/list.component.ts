import { Component, OnInit, ViewChild } from '@angular/core';
import { STColumn, STComponent, ReuseTabService, STChange } from '@delon/abc';
import { SFSchema, SFComponent } from '@delon/form';
import { BasePageComponent } from '@shared/base-page/base-page.component';
import { Router, ActivatedRoute } from '@angular/router';
import { EventBusService } from 'app/service/event-bus.service';
import { TranslateService } from '@ngx-translate/core';
import { SystemQueryService } from 'app/service/core/system-query.service';
import { TitleService } from '@delon/theme';

@Component({
  selector: 'app-sys-query-mgt-list',
  templateUrl: './list.component.html',
})
export class SysQueryMgtListComponent extends BasePageComponent implements OnInit {
  stData = {
    list: null,
    total: null,
    pageSize: 2,
    defaultSort: ['updateTs,desc'],
    sort: ['updateTs,desc']
  };

  searchSchema: SFSchema = {
    properties: {
      'identifier.contains': {
        type: 'string',
        title: this.translate('identifier'),
      },
    },
  };

  @ViewChild('st') st: STComponent;
  @ViewChild('sf') sf: SFComponent;

  columns: STColumn[] = [
    { i18n: 'seq', title: '', type: 'no', noIndex: 1, width: '50px' },
    { i18n: 'identifier', title: '', index: 'identifier', sort: true },
    { i18n: 'role', title: '', index: 'roles', width: '150px' },
    {
      i18n: 'createTs',
      title: '',
      type: 'date',
      index: 'createTs',
      dateFormat: 'YYYY-MM-DD HH:mm:ss',
      width: '155px',
      sort: true,
    },
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
      width: '155px',
      buttons: [
        { i18n: 'delete', type: 'del', click: this.delete },
        { i18n: 'edit', click: this.edit },
        { i18n: 'view', click: this.view },
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
    private systemQueryService: SystemQueryService,
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
    // this.setTitle('title.sys-query', null, this.translate('list'));
    this.loadTable();
  }

  eventHandler(event: string): void {

  }

  loadTable() {
    const tmpParams = Object.assign({page: this.st.pi - 1, size: this.stData.pageSize, sort: this.stData.sort}, this.sf.value);

    this.loading = true;
    this.systemQueryService.query(tmpParams).subscribe(resp => {
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

  delete(item) {
    console.log(item);
  }

  view(item) {
    console.log(item);
  }

  edit(item) {
    console.log(item);
  }


  add() {
    console.log(this.st.pi);
    // this.modal
    //   .createStatic(FormEditComponent, { i: { id: 0 } })
    //   .subscribe(() => this.st.reload());
  }
}
