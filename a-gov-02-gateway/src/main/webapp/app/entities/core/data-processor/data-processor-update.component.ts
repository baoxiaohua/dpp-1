import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDataProcessor } from 'app/shared/model/core/data-processor.model';
import { DataProcessorService } from './data-processor.service';

@Component({
    selector: 'jhi-data-processor-update',
    templateUrl: './data-processor-update.component.html'
})
export class DataProcessorUpdateComponent implements OnInit {
    dataProcessor: IDataProcessor;
    isSaving: boolean;
    createTs: string;
    updateTs: string;

    constructor(private dataProcessorService: DataProcessorService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dataProcessor }) => {
            this.dataProcessor = dataProcessor;
            this.createTs = this.dataProcessor.createTs != null ? this.dataProcessor.createTs.format(DATE_TIME_FORMAT) : null;
            this.updateTs = this.dataProcessor.updateTs != null ? this.dataProcessor.updateTs.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.dataProcessor.createTs = this.createTs != null ? moment(this.createTs, DATE_TIME_FORMAT) : null;
        this.dataProcessor.updateTs = this.updateTs != null ? moment(this.updateTs, DATE_TIME_FORMAT) : null;
        if (this.dataProcessor.id !== undefined) {
            this.subscribeToSaveResponse(this.dataProcessorService.update(this.dataProcessor));
        } else {
            this.subscribeToSaveResponse(this.dataProcessorService.create(this.dataProcessor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDataProcessor>>) {
        result.subscribe((res: HttpResponse<IDataProcessor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
