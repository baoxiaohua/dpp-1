import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    SystemQueryComponent,
    SystemQueryDetailComponent,
    SystemQueryUpdateComponent,
    SystemQueryDeletePopupComponent,
    SystemQueryDeleteDialogComponent,
    systemQueryRoute,
    systemQueryPopupRoute
} from './';

const ENTITY_STATES = [...systemQueryRoute, ...systemQueryPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SystemQueryComponent,
        SystemQueryDetailComponent,
        SystemQueryUpdateComponent,
        SystemQueryDeleteDialogComponent,
        SystemQueryDeletePopupComponent
    ],
    entryComponents: [SystemQueryComponent, SystemQueryUpdateComponent, SystemQueryDeleteDialogComponent, SystemQueryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewaySystemQueryModule {}
