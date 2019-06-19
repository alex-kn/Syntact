package com.alexkn.syntact.presentation.bucketlist;

import com.alexkn.syntact.domain.model.Bucket;

interface ViewModelCallback {

    void delete(Bucket bucket);
}
