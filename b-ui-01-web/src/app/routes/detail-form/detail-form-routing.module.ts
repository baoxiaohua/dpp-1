import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DetailFormFormItemComponent } from './form-item/form-item.component';

const routes: Routes = [

  { path: 'form-item', component: DetailFormFormItemComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DetailFormRoutingModule { }
