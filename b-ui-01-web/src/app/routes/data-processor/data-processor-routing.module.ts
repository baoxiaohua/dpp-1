import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DataProcessorListComponent } from './list/list.component';
import { DataProcessorEditComponent } from './edit/edit.component';

const routes: Routes = [
  {
    path: 'list',
    component: DataProcessorListComponent,
  },
  {
    path: 'edit/:id',
    component: DataProcessorEditComponent,
  },
  {
    path: '',
    component: DataProcessorListComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DataProcessorRoutingModule { }
