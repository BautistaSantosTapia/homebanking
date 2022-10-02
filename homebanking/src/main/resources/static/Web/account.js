//const url = new URL("http://localhost:8080/web/account.html?id=1");
let info = window.location;
console.log(window.location/*.href .search.split("?id=").join("") */);
let buscador = new URLSearchParams(info.search);
console.log(buscador.get('id'));
let elNumero = buscador.get('id')
console.log(elNumero);


const app = Vue.
createApp({
    data(){
        return{
            cuenta1:[],
            transacciones:[],
        }
    },
    created(){
        this.TransactionGet()
    },
    methods:{
        TransactionGet(){
            axios.get(`/api/accounts/${elNumero}`)
            .then((response) => {
                console.log(response);
                 this.cuenta1 = response.data
                console.log(this.cuenta1);
                console.log(this.cuenta1.id);
                this.transacciones = this.cuenta1.transacciones
                console.log(this.transacciones);
                this.transacciones.sort(function(a,b){
                    return b.id - a.id
                })
                console.log(this.transacciones);
                // de mayor a menor
            })
            .catch((error)=>{console.log(error);})
        },
        plata(monto){
            newMonto = new Intl.NumberFormat('en-US',{style: 'currency',currency: 'USD',maximumFractionDigits: 1}).format(monto);
            return newMonto;
            //new Intl.NumberFormat('en-US', {style: 'currency',currency: 'USD',minimumFractionDigits: 0});
        },
        plata2(monto){
            newMonto2 = new Intl.NumberFormat('en-US',{style: 'currency',currency: 'USD',maximumFractionDigits: 0}).format(monto);
            return newMonto2;
        },
        fechaLinda(date){
            fecha = new Date(date).toLocaleDateString();
            return fecha;
            //convierte un dato en una fecha y la retorna en un formato mas legible
        },
        fechaLinda2(date2){
            fecha2 = new Date(date2).toLocaleTimeString();
            return fecha2;
        },
        postCerrar(){
          axios.post('/api/logout').then(response => console.log('signed out!!!'))
        },
    },
    computed:{
        
    },
}).mount('#app')