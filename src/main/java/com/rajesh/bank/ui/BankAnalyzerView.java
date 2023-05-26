/*
 Copyright 2023 Google LLC

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.rajesh.bank.ui;

import java.util.List;

import com.rajesh.bank.core.BankTransaction;

public interface BankAnalyzerView {

    public void setBankTransactions(List<BankTransaction> bankTranasction);

    public void clearSearch();

    public void search();

    public void uploadFile();

}
