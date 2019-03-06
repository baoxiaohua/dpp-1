import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GatewayDataProcessorModule as CoreDataProcessorModule } from './core/data-processor/data-processor.module';
import { GatewayDataSubProcessorModule as CoreDataSubProcessorModule } from './core/data-sub-processor/data-sub-processor.module';
import { GatewayDataProcessorParameterModule as CoreDataProcessorParameterModule } from './core/data-processor-parameter/data-processor-parameter.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CoreDataProcessorModule,
        CoreDataSubProcessorModule,
        CoreDataProcessorParameterModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEntityModule {}
