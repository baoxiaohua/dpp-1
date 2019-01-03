import { Component, OnInit, ViewChild } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { SFSchema, SFUISchema } from '@delon/form';
import { BasePageComponent } from '@shared/base-page/base-page.component';
import { Router, ActivatedRoute } from '@angular/router';
import { ReuseTabService } from '@delon/abc';
import { EventBusService } from 'app/service/event-bus.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-sys-query-mgt-edit',
  templateUrl: './edit.component.html',
})
export class SysQueryMgtEditComponent extends BasePageComponent implements OnInit {
  record: any = {};
  i: any;
  schema: SFSchema = {
    properties: {
      no: { type: 'string', title: '编号' },
      owner: { type: 'string', title: '姓名', maxLength: 15 },
      callNo: { type: 'number', title: '调用次数' },
      href: { type: 'string', title: '链接', format: 'uri' },
      description: { type: 'string', title: '描述', maxLength: 140 },
    },
    required: ['owner', 'callNo', 'href', 'description'],
  };
  ui: SFUISchema = {
    '*': {
      spanLabelFixed: 100,
      grid: { span: 12 },
    },
    $no: {
      widget: 'text'
    },
    $href: {
      widget: 'string',
    },
    $description: {
      widget: 'textarea',
      grid: { span: 24 },
    },
  };

  constructor(
    router: Router,
    activatedRoute: ActivatedRoute,
    reuseTabService: ReuseTabService,
    eventBusService: EventBusService,
    translateService: TranslateService,
    private msgSrv: NzMessageService,
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
    // if (this.record.id > 0)
    // this.http.get(`/user/${this.record.id}`).subscribe(res => (this.i = res));
  }

  save(value: any) {
    // this.http.post(`/user/${this.record.id}`, value).subscribe(res => {
    //   this.msgSrv.success('保存成功');
    //   this.modal.close(true);
    // });
  }

  close() {
    // this.modal.destroy();
  }
}
