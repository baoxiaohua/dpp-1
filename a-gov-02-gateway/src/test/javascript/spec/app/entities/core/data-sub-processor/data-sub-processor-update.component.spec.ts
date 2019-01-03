/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { DataSubProcessorUpdateComponent } from 'app/entities/core/data-sub-processor/data-sub-processor-update.component';
import { DataSubProcessorService } from 'app/entities/core/data-sub-processor/data-sub-processor.service';
import { DataSubProcessor } from 'app/shared/model/core/data-sub-processor.model';

describe('Component Tests', () => {
    describe('DataSubProcessor Management Update Component', () => {
        let comp: DataSubProcessorUpdateComponent;
        let fixture: ComponentFixture<DataSubProcessorUpdateComponent>;
        let service: DataSubProcessorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [DataSubProcessorUpdateComponent]
            })
                .overrideTemplate(DataSubProcessorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataSubProcessorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataSubProcessorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataSubProcessor(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataSubProcessor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataSubProcessor();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataSubProcessor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
