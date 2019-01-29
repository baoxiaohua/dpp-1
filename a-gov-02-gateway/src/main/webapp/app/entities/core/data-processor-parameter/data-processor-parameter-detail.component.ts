import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDataProcessorParameter } from 'app/shared/model/core/data-processor-parameter.model';

@Component({
    selector: 'jhi-data-processor-parameter-detail',
    templateUrl: './data-processor-parameter-detail.component.html'
})
export class DataProcessorParameterDetailComponent implements OnInit {
    dataProcessorParameter: IDataProcessorParameter;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataProcessorParameter }) => {
            this.dataProcessorParameter = dataProcessorParameter;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
