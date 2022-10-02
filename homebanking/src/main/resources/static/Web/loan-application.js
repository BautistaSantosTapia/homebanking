const app = Vue.
createApp({
    data(){
        return{
            cliente1:[],
            cuentas1:[],
            prestamos1:[],
            tipo:"",
            cuotas:0,
            monto:0,
            destino:"",

        }
    },
    created(){
        console.log("llllllllllllllll");
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
                this.prestamos1 = this.cliente1.loans;
                console.log(this.cliente1.loans);
            })
            .catch((error)=>console.log(error))
        },
        plata(monto){
            newMonto = new Intl.NumberFormat('en-US',{style: 'currency',currency: 'USD',maximumFractionDigits: 1}).format(monto);
            return newMonto;
        },
        postCerrar(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
        },
        
        confirmacion(){
            swal({
                title: "Estas seguro/a de solicitar este prestamo?",
                text: "Recorda que los prestamos tienen una tasa de credito, una vez que aceptes no habra marcha atras",
                icon: "warning",
                buttons: true,
                dangerMode: true,
              })
                .then((willDelete) => {
                    if (willDelete) {
                        swal("El prestamo se ha completado correctamente!", {icon: "success",});

                        axios.post('/api/loans',{name: this.tipo, payments: this.cuotas, amount: this.monto, numero: this.destino},{headers:{'content-type':'application/json'}})
                            .then(response =>{
                                console.log('prestamed!!')
                                window.location.href="/Web/accounts.html"
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
                        swal("Has cancelado el prestamo", {icon: "error",});
                        console.log(this.tipo, this.cuotas, this.monto,  this.destino);
                    }
                });
        },
        
    },
    computed:{
    },
}).mount('#app')

