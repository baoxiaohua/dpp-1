import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystemQuery } from 'app/shared/model/core/system-query.model';
import { SystemQueryService } from './system-query.service';

@Component({
    selector: 'jhi-system-query-delete-dialog',
    templateUrl: './system-query-delete-dialog.component.html'
})
export class SystemQueryDeleteDialogComponent {
    systemQuery: ISystemQuery;

    constructor(
        private systemQueryService: SystemQueryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.systemQueryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'systemQueryListModification',
                content: 'Deleted an systemQuery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-system-query-delete-popup',
    template: ''
})
export class SystemQueryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ systemQuery }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SystemQueryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.systemQuery = systemQuery;
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
