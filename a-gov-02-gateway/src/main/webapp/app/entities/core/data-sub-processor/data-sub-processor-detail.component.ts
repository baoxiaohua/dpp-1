import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDataSubProcessor } from 'app/shared/model/core/data-sub-processor.model';

@Component({
    selector: 'jhi-data-sub-processor-detail',
    templateUrl: './data-sub-processor-detail.component.html'
})
export class DataSubProcessorDetailComponent implements OnInit {
    dataSubProcessor: IDataSubProcessor;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataSubProcessor }) => {
            this.dataSubProcessor = dataSubProcessor;
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
