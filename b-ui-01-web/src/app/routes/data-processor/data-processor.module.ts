import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { DataProcessorRoutingModule } from './data-processor-routing.module';
import { DataProcessorListComponent } from './list/list.component';
import { DataProcessorEditComponent } from './edit/edit.component';
import { CodemirrorModule } from '@nomadreservations/ngx-codemirror';
import { DataProcessorCreateComponent } from './create/create.component';


const COMPONENTS = [
  DataProcessorListComponent,
  DataProcessorEditComponent
];
const COMPONENTS_NOROUNT = [
  DataProcessorCreateComponent];

@NgModule({
  imports: [
    SharedModule,
    CodemirrorModule,
    DataProcessorRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class DataProcessorModule { }
