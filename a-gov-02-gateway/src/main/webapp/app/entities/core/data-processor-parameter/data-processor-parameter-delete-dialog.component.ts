import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataProcessorParameter } from 'app/shared/model/core/data-processor-parameter.model';
import { DataProcessorParameterService } from './data-processor-parameter.service';

@Component({
    selector: 'jhi-data-processor-parameter-delete-dialog',
    templateUrl: './data-processor-parameter-delete-dialog.component.html'
})
export class DataProcessorParameterDeleteDialogComponent {
    dataProcessorParameter: IDataProcessorParameter;

    constructor(
        private dataProcessorParameterService: DataProcessorParameterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataProcessorParameterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dataProcessorParameterListModification',
                content: 'Deleted an dataProcessorParameter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-data-processor-parameter-delete-popup',
    template: ''
})
export class DataProcessorParameterDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataProcessorParameter }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DataProcessorParameterDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dataProcessorParameter = dataProcessorParameter;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
