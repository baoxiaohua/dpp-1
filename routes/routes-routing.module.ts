import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { environment } from '@env/environment';
// layout
import { LayoutDefaultComponent } from '../layout/default/default.component';
import { LayoutFullScreenComponent } from '../layout/fullscreen/fullscreen.component';
import { LayoutPassportComponent } from '../layout/passport/passport.component';
// dashboard pages
import { DashboardComponent } from './dashboard/dashboard.component';
// passport pages
import { UserLoginComponent } from './passport/login/login.component';
import { UserRegisterComponent } from './passport/register/register.component';
import { UserRegisterResultComponent } from './passport/register-result/register-result.component';
// single pages
import { CallbackComponent } from './callback/callback.component';
import { UserLockComponent } from './passport/lock/lock.component';
import { Exception403Component } from './exception/403.component';
import { Exception404Component } from './exception/404.component';
import { Exception500Component } from './exception/500.component';
import { JWTGuard } from '@delon/auth';
import {BasicFormModule} from "./basic-form/basic-form.module";

const routes: Routes = [
  {
    path: '',
    component: LayoutDefaultComponent,
    canActivateChild: [JWTGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      // 业务子模块
      { path: 'data-processor', loadChildren: './data-processor/data-processor.module#DataProcessorModule' },
      { path: 'advanced-form', loadChildren: './advanced-form/advanced-form.module#AdvancedFormModule' },
      { path: 'basic-form', loadChildren: './basic-form/basic-form.module#BasicFormModule' },
      { path: 'detail-page',loadChildren:'./detail-page/detail-page.module#DetailPageModule'},
      { path: 'detail-form',loadChildren:'./detail-form/detail-form.module#DetailFormModule'},
      { path: 'expand-form',loadChildren:'./expand-form/expand-form.module#ExpandFormModule'},
      { path: 'feedback',loadChildren:'./feedback/feedback.module#FeedbackModule'},
      { path: 'personal-center',loadChildren:'./personal-center/personal-center.module#PersonalCenterModule'},

    ]
  },
  // passport
  {
    path: 'passport',
    component: LayoutPassportComponent,
    children: [
      { path: 'login', component: UserLoginComponent, data: { title: '登录', titleI18n: 'pro-login', reuse: false } },
    ]
  },
  // 单页不包裹Layout
  { path: 'callback/:type', component: CallbackComponent, data: { reuse: false } },
  { path: 'lock', component: UserLockComponent, data: { title: '锁屏', titleI18n: 'lock', reuse: false } },
  { path: '403', component: Exception403Component, data: { reuse: false } },
  { path: '404', component: Exception404Component, data: { reuse: false } },
  { path: '500', component: Exception500Component, data: { reuse: false } },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: environment.useHash })],
  exports: [RouterModule]
})
export class RouteRoutingModule { }
