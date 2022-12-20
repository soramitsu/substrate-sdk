//
//  ContentView.swift
//  ios sample
//
//  Created by Ivan Shlyapkin on 04.05.2022.
//

import SwiftUI
import SubstrateSdk

struct ContentView: View {
    var body: some View {
        VStack {
            Text("Hello, world!")
                .padding()
            Text("Hello, world!")
                .padding()
            Text("Hello, world!")
                .padding()
            Text("Hello, world!")
                .padding()
            Button("test") {
                load()
            }
        }
        
    }
    
    func load() {
        var service = SocketService()
        service.start(
            url: "wss://ws.parachain-collator-1.c1.sora2.soramitsu.co.jp",
            remainPaused: false
        )
        var result = service.executeAsync(
            request: RuntimeRequest(
                method: "chain_getRuntimeVersion",
                params: []
            ),
            deliveryType: DeliveryType.atLeastOnce,
            mapper: RuntimeVersionMapper()
        ) { result, error in
            if let res = result {
                print("specVersion: " + String(describing: (res as! NullableContainer<RuntimeVersion>).result!.specVersion))
            }
            if let e = error {
                print("error: " + String(describing: e))
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
