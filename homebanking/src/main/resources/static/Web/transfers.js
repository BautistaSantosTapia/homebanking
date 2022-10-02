const app = Vue.
createApp({
    data(){
        return{
            origen:"",
            who:"",
            whoText:"",
            descripcion:"",
            monto:0,
            cliente1:[],
            cuentas1:[],
            arrayCuentasTrue:[],
            radi:[],
            rd:"",
        }
    },
    created(){
        console.log("tttttttttttt");
        this.getAxios()

    },
    methods:{
        getAxios(){
            axios.get('/api/clients/current')
            .then((response)=>{
                console.log(response)
                this.cliente1 = response.data
                console.log(this.cliente1);
                console.log(this.cliente1.id);
                console.log(this.cliente1.nombre);
                console.log(this.cliente1.apellido);
                console.log(this.cliente1.mail);
                this.cuentas1 = this.cliente1.cuentas;
                console.log(this.cliente1.cuentas);

                this.filtroTrue()
            })
            .catch((error)=>console.log(error))
        },
        plata(monto){
            newMonto = new Intl.NumberFormat('en-US',{style: 'currency',currency: 'USD',maximumFractionDigits: 1}).format(monto);
            return newMonto;
        },
        filtroTrue(){
            this.arrayCuentasTrue = this.cuentas1.filter(cuenta => cuenta.activeAccount == true)
            console.log(this.arrayCuentasTrue);
        },
        postCerrar(){
          axios.post('/api/logout').then(response => console.log('signed out!!!'))
        },
        /*if whoText.checked == true
            ${whoText}

          else if whoText2.checked == true
            ${whoText2}
        */
        /*if whoText == ""
            ${whoText2}

          else if whoText2 == ""
            ${whoText}
        */
        postTransaccion(){
            axios.post('/api/transactions',`amount=${this.monto}&description=${this.descripcion}&numero=${this.origen}&numero2=${this.whoText}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>{
                console.log('transaccioned')
            })
            .catch(function (error) {
                if (error.response) {
                    console.log(error.response.data);
                    swal({
                        title: "error",
                        text: error.response.data, 
                        icon: "error",
                    })
                }}
            );
        },
        confirmacion(){
            swal({
                title: `Estas seguro de realizar la transferencia de $${this.monto} a la cuenta ${this.whoText}?`,
                text: "Una vez que aceptes no habra marcha atras",
                icon: "warning",
                buttons: true,
                dangerMode: true,
              })
                .then((willDelete) => {
                    if (willDelete) {
                        swal("La transaccion se ha realizado correctamente!", {icon: "success",});

                        axios.post('/api/transactions',`amount=${this.monto}&description=${this.descripcion}&numero=${this.origen}&numero2=${this.whoText}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                            .then(response =>{
                                console.log('transaccioned')
                                window.location.href="/Web/transfers.html"
                            })
                            .catch(function (error) {
                                if (error.response) {
                                    console.log(error.response.data);
                                    swal({
                                        title: "error",
                                        text: error.response.data, 
                                        icon: "error",
                                    })
                                }}
                            );
                    } else {
                        swal("Has cancelado la transaccion");
                    }
                });
        },  
 
    },
    computed:{

    },
}).mount('#app')
