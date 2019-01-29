import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IDataProcessorParameter } from 'app/shared/model/core/data-processor-parameter.model';
import { DataProcessorParameterService } from './data-processor-parameter.service';

@Component({
    selector: 'jhi-data-processor-parameter-update',
    templateUrl: './data-processor-parameter-update.component.html'
})
export class DataProcessorParameterUpdateComponent implements OnInit {
    dataProcessorParameter: IDataProcessorParameter;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private dataProcessorParameterService: DataProcessorParameterService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dataProcessorParameter.id !== undefined) {
            this.subscribeToSaveResponse(this.dataProcessorParameterService.update(this.dataProcessorParameter));
        } else {
            this.subscribeToSaveResponse(this.dataProcessorParameterService.create(this.dataProcessorParameter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDataProcessorParameter>>) {
        result.subscribe(
            (res: HttpResponse<IDataProcessorParameter>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
