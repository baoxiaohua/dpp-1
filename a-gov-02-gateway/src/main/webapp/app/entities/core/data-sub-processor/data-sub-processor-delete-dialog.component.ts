import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataSubProcessor } from 'app/shared/model/core/data-sub-processor.model';
import { DataSubProcessorService } from './data-sub-processor.service';

@Component({
    selector: 'jhi-data-sub-processor-delete-dialog',
    templateUrl: './data-sub-processor-delete-dialog.component.html'
})
export class DataSubProcessorDeleteDialogComponent {
    dataSubProcessor: IDataSubProcessor;

    constructor(
        private dataSubProcessorService: DataSubProcessorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataSubProcessorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dataSubProcessorListModification',
                content: 'Deleted an dataSubProcessor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-data-sub-processor-delete-popup',
    template: ''
})
export class DataSubProcessorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataSubProcessor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DataSubProcessorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dataSubProcessor = dataSubProcessor;
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
