import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataProcessor } from 'app/shared/model/core/data-processor.model';

@Component({
    selector: 'jhi-data-processor-detail',
    templateUrl: './data-processor-detail.component.html'
})
export class DataProcessorDetailComponent implements OnInit {
    dataProcessor: IDataProcessor;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataProcessor }) => {
            this.dataProcessor = dataProcessor;
        });
    }

    previousState() {
        window.history.back();
    }
}
