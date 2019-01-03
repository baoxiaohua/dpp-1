import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GatewayComputationGroupModule as CoreComputationGroupModule } from './core/computation-group/computation-group.module';
import { GatewayComputationModule as CoreComputationModule } from './core/computation/computation.module';
import { GatewaySystemQueryModule as CoreSystemQueryModule } from './core/system-query/system-query.module';
import { GatewayDataProcessorModule as CoreDataProcessorModule } from './core/data-processor/data-processor.module';
import { GatewayDataSubProcessorModule as CoreDataSubProcessorModule } from './core/data-sub-processor/data-sub-processor.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CoreComputationGroupModule,
        CoreComputationModule,
        CoreSystemQueryModule,
        CoreDataProcessorModule,
        CoreDataSubProcessorModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEntityModule {}
