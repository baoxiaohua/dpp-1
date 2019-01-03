/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { DataSubProcessorDeleteDialogComponent } from 'app/entities/core/data-sub-processor/data-sub-processor-delete-dialog.component';
import { DataSubProcessorService } from 'app/entities/core/data-sub-processor/data-sub-processor.service';

describe('Component Tests', () => {
    describe('DataSubProcessor Management Delete Component', () => {
        let comp: DataSubProcessorDeleteDialogComponent;
        let fixture: ComponentFixture<DataSubProcessorDeleteDialogComponent>;
        let service: DataSubProcessorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [DataSubProcessorDeleteDialogComponent]
            })
                .overrideTemplate(DataSubProcessorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataSubProcessorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataSubProcessorService);
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
