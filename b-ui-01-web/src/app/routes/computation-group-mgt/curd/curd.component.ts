import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent, ReuseTabService } from '@delon/abc';
import { SFSchema } from '@delon/form';
import { BasePageComponent } from '@shared/base-page/base-page.component';
import { BasePageInterface } from '@shared/base-page/base-page.interface';
import { Router, ActivatedRoute } from '@angular/router';
import { EventBusService } from 'app/service/event-bus.service';
import { TranslateService } from '@ngx-translate/core';
import { ComputationGroupMgtCurdEditComponent } from './edit/edit.component';

@Component({
  selector: 'app-computation-group-mgt-curd',
  templateUrl: './curd.component.html',
})
export class ComputationGroupMgtCurdComponent extends BasePageComponent
  implements OnInit, BasePageInterface {

  url = `/user`;

  searchSchema: SFSchema = {
    properties: {
      no: {
        type: 'string',
        title: '编号',
      },
    },
  };

  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: '编号', index: 'no' },
    { title: '调用次数', type: 'number', index: 'callNo' },
    { title: '头像', type: 'img', width: '50px', index: 'avatar' },
    { title: '时间', type: 'date', index: 'updatedAt' },
    {
      title: '操作',
      buttons: [
        { text: '查看', click: (item: any) => `/form/${item.id}` },
        { text: '编辑', type: 'static', component: ComputationGroupMgtCurdEditComponent, click: 'reload' },
      ],
    },
  ];

  constructor(
    router: Router,
    activatedRoute: ActivatedRoute,
    reuseTabService: ReuseTabService,
    eventBusService: EventBusService,
    translateService: TranslateService,
    private http: _HttpClient,
    private modal: ModalHelper,
  ) {
    super(
      router,
      activatedRoute,
      reuseTabService,
      eventBusService,
      translateService,
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  initVariable(params, queryParams): void {}

  loadPage(): void {
    // this.setTitle('title.computation-group', null, '管理页');
  }

  eventHandler(event: string): void {
    console.log('DashboardComponent', 'got event ' + event);
  }

  add() {
    // this.modal
    //   .createStatic(FormEditComponent, { i: { id: 0 } })
    //   .subscribe(() => this.st.reload());
  }

  test(e) {
    console.log(e);
  }
}
