import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ExpandFormExpandComponent } from './expand/expand.component';

const routes: Routes = [

  { path: 'expand', component: ExpandFormExpandComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExpandFormRoutingModule { }
