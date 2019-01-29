import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    DataProcessorParameterComponent,
    DataProcessorParameterDetailComponent,
    DataProcessorParameterUpdateComponent,
    DataProcessorParameterDeletePopupComponent,
    DataProcessorParameterDeleteDialogComponent,
    dataProcessorParameterRoute,
    dataProcessorParameterPopupRoute
} from './';

const ENTITY_STATES = [...dataProcessorParameterRoute, ...dataProcessorParameterPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DataProcessorParameterComponent,
        DataProcessorParameterDetailComponent,
        DataProcessorParameterUpdateComponent,
        DataProcessorParameterDeleteDialogComponent,
        DataProcessorParameterDeletePopupComponent
    ],
    entryComponents: [
        DataProcessorParameterComponent,
        DataProcessorParameterUpdateComponent,
        DataProcessorParameterDeleteDialogComponent,
        DataProcessorParameterDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayDataProcessorParameterModule {}
