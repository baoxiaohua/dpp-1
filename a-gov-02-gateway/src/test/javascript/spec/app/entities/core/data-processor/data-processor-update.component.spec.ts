/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { DataProcessorUpdateComponent } from 'app/entities/core/data-processor/data-processor-update.component';
import { DataProcessorService } from 'app/entities/core/data-processor/data-processor.service';
import { DataProcessor } from 'app/shared/model/core/data-processor.model';

describe('Component Tests', () => {
    describe('DataProcessor Management Update Component', () => {
        let comp: DataProcessorUpdateComponent;
        let fixture: ComponentFixture<DataProcessorUpdateComponent>;
        let service: DataProcessorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [DataProcessorUpdateComponent]
            })
                .overrideTemplate(DataProcessorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataProcessorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataProcessorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataProcessor(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataProcessor = entity;
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
                    const entity = new DataProcessor();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataProcessor = entity;
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
