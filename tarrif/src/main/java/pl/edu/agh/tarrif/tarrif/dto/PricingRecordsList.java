package pl.edu.agh.tarrif.tarrif.dto;

import java.util.List;

public record PricingRecordsList(
        List<PricingRecord> tarrifs
)
{ }
