import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    DataProcessorComponent,
    DataProcessorDetailComponent,
    DataProcessorUpdateComponent,
    DataProcessorDeletePopupComponent,
    DataProcessorDeleteDialogComponent,
    dataProcessorRoute,
    dataProcessorPopupRoute
} from './';

const ENTITY_STATES = [...dataProcessorRoute, ...dataProcessorPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DataProcessorComponent,
        DataProcessorDetailComponent,
        DataProcessorUpdateComponent,
        DataProcessorDeleteDialogComponent,
        DataProcessorDeletePopupComponent
    ],
    entryComponents: [
        DataProcessorComponent,
        DataProcessorUpdateComponent,
        DataProcessorDeleteDialogComponent,
        DataProcessorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayDataProcessorModule {}
