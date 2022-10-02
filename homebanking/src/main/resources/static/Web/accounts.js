const app = Vue.
createApp({
    data(){
        return{
            cliente1:[],
            cuentas1:[],
            prestamos1:[],
            cuentaABorrar:{},
            arrayCuentasTrue:[],
        }
    },
    created(){
        this.getAxios()
    },
    methods:{
        getAxios(){
            axios.get('/api/clients/current')
            /*axios.get('http://localhost:8080/api/clients/current',{headers:{'accept':'application/xml'}})*/
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

                this.filtroTrue()
            })
            .catch((error)=>console.log(error))
        },
        plata(monto){
            newMonto = new Intl.NumberFormat('en-US',{style: 'currency',currency: 'USD',maximumFractionDigits: 0}).format(monto);
            return newMonto;
            //formatea un numero segun como se escribe en ese pais, le da un estilo monetario, en dolares y sin decimales
        },
        fecha(date){
            fecha = new Date(date).toLocaleDateString();
            return fecha;
            //convierte un dato en una fecha y la retorna en un formato mas legible
        },
        fecha2(date2){
            fecha2 = new Date(date2).toLocaleTimeString();
            return fecha2;
        },
        postCerrar(){
          axios.post('/api/logout').then(response => console.log('signed out!!!'))
        },
        filtroTrue(){
            this.arrayCuentasTrue = this.cuentas1.filter(cuenta => cuenta.activeAccount == true)
            console.log(this.arrayCuentasTrue);
        },
        confirmacionDelete(id){

                swal({
                    title: "Estas seguro/a de borrar esta cuenta?",
                    text: "Recorda que una vez que aceptes no habra marcha atras",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                  })
                    .then((willDelete) => {
                        if (willDelete) {
                            swal("La cuenta se ha borrado correctamente!", {icon: "success",});
    
                            console.log(id);
                            this.cuentaABorrar = this.cuentas1.filter(cuenta => cuenta.id == id)
                            console.log(this.cuentaABorrar);
                            console.log(this.cuentaABorrar[0]);
                            console.log(this.cuentaABorrar[0].activeAccount);
                            this.cuentaABorrar[0].activeAccount = false;
                            console.log(this.cuentaABorrar[0].activeAccount);
                            console.log(this.cuentaABorrar[0].id);
                            axios.patch(`/api/clients/current/accounts/${this.cuentaABorrar[0].id}`)
                            .then((response)=>{
                                console.log("patcheado");
                                window.location.href="/Web/accounts.html"}
                            )
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
                            swal("No has borrado la cuenta", {icon: "error",});
                        }
                    });
            
        },
    },
    computed:{
        
    },
}).mount('#app')