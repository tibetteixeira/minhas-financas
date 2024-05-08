ALTER TABLE pagamento
    DROP COLUMN tipo_pagamento;

ALTER TABLE gasto
    ADD COLUMN forma_pagamento VARCHAR(20) NOT NULL;
