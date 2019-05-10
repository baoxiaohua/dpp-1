import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BasicFormBasicComponent } from './basic/basic.component';

const routes: Routes = [

  { path: 'basic', component: BasicFormBasicComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BasicFormRoutingModule { }
