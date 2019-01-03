import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    DataSubProcessorComponent,
    DataSubProcessorDetailComponent,
    DataSubProcessorUpdateComponent,
    DataSubProcessorDeletePopupComponent,
    DataSubProcessorDeleteDialogComponent,
    dataSubProcessorRoute,
    dataSubProcessorPopupRoute
} from './';

const ENTITY_STATES = [...dataSubProcessorRoute, ...dataSubProcessorPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DataSubProcessorComponent,
        DataSubProcessorDetailComponent,
        DataSubProcessorUpdateComponent,
        DataSubProcessorDeleteDialogComponent,
        DataSubProcessorDeletePopupComponent
    ],
    entryComponents: [
        DataSubProcessorComponent,
        DataSubProcessorUpdateComponent,
        DataSubProcessorDeleteDialogComponent,
        DataSubProcessorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayDataSubProcessorModule {}
