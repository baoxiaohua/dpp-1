import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataProcessor } from 'app/shared/model/core/data-processor.model';
import { DataProcessorService } from './data-processor.service';

@Component({
    selector: 'jhi-data-processor-delete-dialog',
    templateUrl: './data-processor-delete-dialog.component.html'
})
export class DataProcessorDeleteDialogComponent {
    dataProcessor: IDataProcessor;

    constructor(
        private dataProcessorService: DataProcessorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataProcessorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dataProcessorListModification',
                content: 'Deleted an dataProcessor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-data-processor-delete-popup',
    template: ''
})
export class DataProcessorDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataProcessor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DataProcessorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dataProcessor = dataProcessor;
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
