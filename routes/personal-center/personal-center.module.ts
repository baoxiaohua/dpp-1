import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { PersonalCenterRoutingModule } from './personal-center-routing.module';
import { PersonalCenterPersonalDataComponent } from './personal-data/personal-data.component';

const COMPONENTS = [
  PersonalCenterPersonalDataComponent];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    PersonalCenterRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class PersonalCenterModule { }
