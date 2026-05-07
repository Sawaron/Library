ALTER TABLE public.users
    ADD COLUMN IF NOT EXISTS user_birth_date DATE;

ALTER TABLE public.reservation
    ADD COLUMN IF NOT EXISTS available_date TIMESTAMP;

CREATE INDEX IF NOT EXISTS idx_reservation_active_avail
    ON public.reservation(status, available_date)
    WHERE status = 'ACTIVE';