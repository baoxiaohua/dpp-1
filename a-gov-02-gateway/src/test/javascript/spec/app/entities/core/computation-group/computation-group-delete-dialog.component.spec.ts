/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { ComputationGroupDeleteDialogComponent } from 'app/entities/core/computation-group/computation-group-delete-dialog.component';
import { ComputationGroupService } from 'app/entities/core/computation-group/computation-group.service';

describe('Component Tests', () => {
    describe('ComputationGroup Management Delete Component', () => {
        let comp: ComputationGroupDeleteDialogComponent;
        let fixture: ComponentFixture<ComputationGroupDeleteDialogComponent>;
        let service: ComputationGroupService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ComputationGroupDeleteDialogComponent]
            })
                .overrideTemplate(ComputationGroupDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ComputationGroupDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComputationGroupService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
