import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    ComputationGroupComponent,
    ComputationGroupDetailComponent,
    ComputationGroupUpdateComponent,
    ComputationGroupDeletePopupComponent,
    ComputationGroupDeleteDialogComponent,
    computationGroupRoute,
    computationGroupPopupRoute
} from './';

const ENTITY_STATES = [...computationGroupRoute, ...computationGroupPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ComputationGroupComponent,
        ComputationGroupDetailComponent,
        ComputationGroupUpdateComponent,
        ComputationGroupDeleteDialogComponent,
        ComputationGroupDeletePopupComponent
    ],
    entryComponents: [
        ComputationGroupComponent,
        ComputationGroupUpdateComponent,
        ComputationGroupDeleteDialogComponent,
        ComputationGroupDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayComputationGroupModule {}
