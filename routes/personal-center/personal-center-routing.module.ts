import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PersonalCenterPersonalDataComponent } from './personal-data/personal-data.component';

const routes: Routes = [

  { path: 'personal-data', component: PersonalCenterPersonalDataComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PersonalCenterRoutingModule { }
