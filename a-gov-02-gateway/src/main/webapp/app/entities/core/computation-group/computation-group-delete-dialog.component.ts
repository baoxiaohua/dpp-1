import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComputationGroup } from 'app/shared/model/core/computation-group.model';
import { ComputationGroupService } from './computation-group.service';

@Component({
    selector: 'jhi-computation-group-delete-dialog',
    templateUrl: './computation-group-delete-dialog.component.html'
})
export class ComputationGroupDeleteDialogComponent {
    computationGroup: IComputationGroup;

    constructor(
        private computationGroupService: ComputationGroupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.computationGroupService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'computationGroupListModification',
                content: 'Deleted an computationGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-computation-group-delete-popup',
    template: ''
})
export class ComputationGroupDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ computationGroup }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ComputationGroupDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.computationGroup = computationGroup;
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
