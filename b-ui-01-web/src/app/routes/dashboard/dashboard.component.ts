import { Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BasePageComponent } from '@shared/base-page/base-page.component';
import { BasePageInterface } from '@shared/base-page/base-page.interface';
import { Router, ActivatedRoute } from '@angular/router';
import { ReuseTabService } from '@delon/abc';
import { EventBusService } from 'app/service/event-bus.service';
import { TranslateService } from '@ngx-translate/core';
import { SystemQueryCustomService } from 'app/service/core/custom/system-query.custom.service';
import { SystemQueryService } from 'app/service/core/system-query.service';

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

  constructor(
    router: Router,
    activatedRoute: ActivatedRoute,
    reuseTabService: ReuseTabService,
    eventBusService: EventBusService,
    translateService: TranslateService,
    private layoutService: LayoutService,
    private systemQueryCustomService: SystemQueryCustomService,
    private systemQueryService: SystemQueryService,
    // private _codeMirror: CodemirrorService,
  ) {
    super(router, activatedRoute, reuseTabService, eventBusService, translateService);
  }

  ngOnInit() {
    super.ngOnInit();
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
    this.systemQueryCustomService.query('COMPUTATION_QUERY', null).subscribe(
      resp => {
        console.log(resp);
      }
    );
    // this.systemQueryService.query().subscribe(
    //   resp => {console.log(resp); }
    // );
  }
}
