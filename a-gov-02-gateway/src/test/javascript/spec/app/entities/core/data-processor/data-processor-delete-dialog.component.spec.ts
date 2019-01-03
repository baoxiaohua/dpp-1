/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { DataProcessorDeleteDialogComponent } from 'app/entities/core/data-processor/data-processor-delete-dialog.component';
import { DataProcessorService } from 'app/entities/core/data-processor/data-processor.service';

describe('Component Tests', () => {
    describe('DataProcessor Management Delete Component', () => {
        let comp: DataProcessorDeleteDialogComponent;
        let fixture: ComponentFixture<DataProcessorDeleteDialogComponent>;
        let service: DataProcessorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [DataProcessorDeleteDialogComponent]
            })
                .overrideTemplate(DataProcessorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataProcessorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataProcessorService);
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
