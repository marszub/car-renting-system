export default function HttpPromise(timeout, promise, effect) {
    var firstPromiseReturned = false;

    Promise.race([
        promise,
        new Promise((resolve, reject) => {
            setTimeout(() => {
                if(!firstPromiseReturned) {
                    effect();
                }
            }, timeout, null)
        })
    ]).then(() => firstPromiseReturned = true)
}
