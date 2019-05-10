import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DetailPageDetailComponent } from './detail/detail.component';

const routes: Routes = [

  { path: 'detail', component: DetailPageDetailComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DetailPageRoutingModule { }
