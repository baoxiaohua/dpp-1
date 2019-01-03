/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { SystemQueryDeleteDialogComponent } from 'app/entities/core/system-query/system-query-delete-dialog.component';
import { SystemQueryService } from 'app/entities/core/system-query/system-query.service';

describe('Component Tests', () => {
    describe('SystemQuery Management Delete Component', () => {
        let comp: SystemQueryDeleteDialogComponent;
        let fixture: ComponentFixture<SystemQueryDeleteDialogComponent>;
        let service: SystemQueryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SystemQueryDeleteDialogComponent]
            })
                .overrideTemplate(SystemQueryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SystemQueryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SystemQueryService);
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
