import { Component, OnInit } from '@angular/core';
import { _HttpClient, TitleService } from '@delon/theme';
import { BasePageComponent } from '@shared/base-page/base-page.component';
import { BasePageInterface } from '@shared/base-page/base-page.interface';
import { Router, ActivatedRoute } from '@angular/router';
import { ReuseTabService } from '@delon/abc';
import { EventBusService } from 'app/service/event-bus.service';
import { TranslateService } from '@ngx-translate/core';

import { CodemirrorService } from '@nomadreservations/ngx-codemirror';

import 'codemirror/mode/sql/sql';
import 'codemirror/addon/hint/show-hint.js';
import 'codemirror/addon/hint/sql-hint.js';

import { LayoutService } from 'app/service/layout.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent extends BasePageComponent
  implements OnInit, BasePageInterface {

    code = '';
    cmOptions: any = {
      lineNumbers: true,
      mode: { name: 'text/x-pgsql' },
      theme: 'dracula',
      gutters: ['CodeMirror-lint-markers'],
      lint: true,
      extraKeys: {
        Tab: 'autocomplete',
      }
    };

    startValue;
    endValue;

    disabledStartDate = (startValue: Date): boolean => {
      if (!startValue || !this.endValue) {
        return false;
      }
      console.log(startValue.getTime());
      return startValue.getTime() >= this.endValue.getTime();
    }

    disabledEndDate = (endValue: Date): boolean => {
      if (!endValue || !this.startValue) {
        return false;
      }
      return endValue.getTime() <= this.startValue.getTime();
    }






  constructor(
    router: Router,
    activatedRoute: ActivatedRoute,
    reuseTabService: ReuseTabService,
    eventBusService: EventBusService,
    translateService: TranslateService,
    titleService: TitleService,
    private layoutService: LayoutService,
    // private _codeMirror: CodemirrorService,
  ) {
    super(router, activatedRoute, reuseTabService, eventBusService, translateService, titleService);
  }

  ngOnInit() {
    super.ngOnInit();

    this.setTitle('仪表盘', null);
    // this._codeMirror.instance$.subscribe((editor) => {
    //   editor.setSize('100%', this.layoutService.contentHeight);
    //   console.log(editor);
    // });
  }

  initVariable(params, queryParams): void {}

  loadPage(): void {
    console.log('DashboardComponent', 'loadPage()');
    // this.setTitle('title.dashboard', '1', '什么什么');

  }

  eventHandler(event: string): void {
    console.log('DashboardComponent', 'got event ' + event);
  }

  test() {

    // this.systemQueryService.query().subscribe(
    //   resp => {console.log(resp); }
    // );
  }
}
