import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    ComputationComponent,
    ComputationDetailComponent,
    ComputationUpdateComponent,
    ComputationDeletePopupComponent,
    ComputationDeleteDialogComponent,
    computationRoute,
    computationPopupRoute
} from './';

const ENTITY_STATES = [...computationRoute, ...computationPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ComputationComponent,
        ComputationDetailComponent,
        ComputationUpdateComponent,
        ComputationDeleteDialogComponent,
        ComputationDeletePopupComponent
    ],
    entryComponents: [ComputationComponent, ComputationUpdateComponent, ComputationDeleteDialogComponent, ComputationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayComputationModule {}
