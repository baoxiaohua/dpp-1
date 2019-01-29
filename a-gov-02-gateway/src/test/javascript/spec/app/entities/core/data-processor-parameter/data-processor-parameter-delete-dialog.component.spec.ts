/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { DataProcessorParameterDeleteDialogComponent } from 'app/entities/core/data-processor-parameter/data-processor-parameter-delete-dialog.component';
import { DataProcessorParameterService } from 'app/entities/core/data-processor-parameter/data-processor-parameter.service';

describe('Component Tests', () => {
    describe('DataProcessorParameter Management Delete Component', () => {
        let comp: DataProcessorParameterDeleteDialogComponent;
        let fixture: ComponentFixture<DataProcessorParameterDeleteDialogComponent>;
        let service: DataProcessorParameterService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [DataProcessorParameterDeleteDialogComponent]
            })
                .overrideTemplate(DataProcessorParameterDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataProcessorParameterDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataProcessorParameterService);
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
