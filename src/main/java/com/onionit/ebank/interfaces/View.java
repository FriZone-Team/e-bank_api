package com.onionit.ebank.interfaces;

public interface View {
    interface Always {
    }

    interface List extends Always {
    }

    interface Detail extends List {
    }

    interface Created extends Detail {
    }

    interface Updated extends Detail {
    }

    interface Deleted extends Detail {
    }

    interface Admin extends Detail {
    }

    interface Owned extends Detail {
    }
}
