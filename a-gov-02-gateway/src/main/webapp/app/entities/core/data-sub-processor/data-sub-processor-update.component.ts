import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils } from 'ng-jhipster';

import { IDataSubProcessor } from 'app/shared/model/core/data-sub-processor.model';
import { DataSubProcessorService } from './data-sub-processor.service';

@Component({
    selector: 'jhi-data-sub-processor-update',
    templateUrl: './data-sub-processor-update.component.html'
})
export class DataSubProcessorUpdateComponent implements OnInit {
    dataSubProcessor: IDataSubProcessor;
    isSaving: boolean;
    createTs: string;
    updateTs: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private dataSubProcessorService: DataSubProcessorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dataSubProcessor }) => {
            this.dataSubProcessor = dataSubProcessor;
            this.createTs = this.dataSubProcessor.createTs != null ? this.dataSubProcessor.createTs.format(DATE_TIME_FORMAT) : null;
            this.updateTs = this.dataSubProcessor.updateTs != null ? this.dataSubProcessor.updateTs.format(DATE_TIME_FORMAT) : null;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.dataSubProcessor.createTs = this.createTs != null ? moment(this.createTs, DATE_TIME_FORMAT) : null;
        this.dataSubProcessor.updateTs = this.updateTs != null ? moment(this.updateTs, DATE_TIME_FORMAT) : null;
        if (this.dataSubProcessor.id !== undefined) {
            this.subscribeToSaveResponse(this.dataSubProcessorService.update(this.dataSubProcessor));
        } else {
            this.subscribeToSaveResponse(this.dataSubProcessorService.create(this.dataSubProcessor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDataSubProcessor>>) {
        result.subscribe((res: HttpResponse<IDataSubProcessor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
