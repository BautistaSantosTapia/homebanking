  const app = Vue.

  createApp({
    data() {
      return {
        clientes: [],
        prestamos:[],
        nombre: "",
        apellido: "",
        mail: "",
        id: "",
        nombreP:[],
        cuotas:"",
        montoMax:"",
        pago:[],
      }
    },
    created(){
        this.functAxiosGet()
    },
    methods: {
        functAxiosGet(){
        axios.get('/api/clients')
        .then((response)=>{
          console.log(response);
          this.clientes = response.data
          console.log(this.clientes);
        }).catch(()=>{console.log("ups!")})
        axios.get('/api/loans')
        .then((response)=>{
          console.log(response);
          this.prestamos = response.data
          console.log(this.prestamos);
        }).catch(()=>{console.log("ups!")})
        },

        postCerrar(){
          axios.post('/api/logout').then(response => console.log('signed out!!!'))
        },
        functAxiosPost(){
          if (this.nombre == null || this.apellido == null || this.mail == null || this.id == null ) {
            axios.post('/rest/clients',{nombre: this.nombre, apellido: this.apellido, mail: this.mail,id: this.id})
            .then(()=>{this.functAxiosGet()})
            .catch((error)=>{console.log(error);})
          }

        },
        /*
        functAxiosPost(){
          if (this.botonEditar == true) {
            axios.put(`/rest/clients/${this.id}`, {nombre: this.nombre, apellido: this.apellido, mail: this.mail,id: this.id})
            //no me toma el this.id de la 32
            .then(()=>{this.botonEditar = false;})
            .catch(()=>console.log("no funca"))
          }else{
            axios.post('/rest/clients',{nombre: this.nombre, apellido: this.apellido, mail: this.mail,id: this.id})
            .then((response)=>{console.log(response);})
            .catch((error)=>{console.log(error);})
          }
        },*/

        functAxiosDelete(id){
          axios.delete(`/rest/clients/${id}`)
          .then(()=>{this.functAxiosGet()})
          .catch((error)=>{console.log(error);})
        },

        
        /* functAxiosEdit(id){
          axios.get(`/rest/clients/${id}`)
          .then((response)=>{
            this.id = response.data.id
            this.botonEditar = true
            this.nombre = response.data.nombre
            this.apellido = response.data.apellido
            this.mail = response.data.mail
          })
        },  */
        functAxiosEdit(id){
          let nombreEdit
          nombreEdit = prompt("pone tu nuevo nombre")
          if (nombreEdit == ""){
            alert("tiene que ser un nombre");
            nombreEdit = prompt("pone tu nuevo nombre")
          }
          nombre = nombreEdit
          axios.patch(`/rest/clients/${id}`, {nombre})
          .then(()=>{console.log(id);
          this.functAxiosGet()})
          .catch((error)=>{console.log(error);})

          let apellidoEdit
          apellidoEdit = prompt("pone tu nuevo apellido")
          if (apellidoEdit == ""){
            alert("tiene que ser un apellido");
            apellidoEdit = prompt("pone tu nuevo apellido")
          }
          apellido = apellidoEdit
          axios.patch(`/rest/clients/${id}`, {apellido})
          .then(()=>{console.log(id);
          this.functAxiosGet()})
          .catch((error)=>{console.log(error);})

          let mailEdit
          mailEdit = prompt("pone un nuevo mail")
          if (!mailEdit.includes("@") || mailEdit == ""){
            alert("tiene que ser un mail");
            mailEdit = prompt("pone un nuevo mail")
          }
          mail = mailEdit
          axios.patch(`/rest/clients/${id}`, {mail})
          .then(()=>{console.log(id);
          this.functAxiosGet()})
          .catch((error)=>{console.log(error);})
        },

        confirmacion(){
          axios.post('/api/clientLoans',`name=${this.nombreP}&maxAmount=${this.montoMax}&payments=${this.pago}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>{
                console.log('prestamed!!')
                window.location.href="/manager.html"
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

    },
    computed: {
    },
  }).mount('#app')